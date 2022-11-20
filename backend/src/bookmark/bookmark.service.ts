import {
  Injectable,
  NotFoundException,
  UnprocessableEntityException,
} from '@nestjs/common'
import { Prisma, Source, UpdatedFrom } from '@prisma/client'
import { GroupRepository } from 'src/group/group.repository'
import { UserRepository } from 'src/user/user.repository'
import { BookmarkRepository } from './bookmark.repository'
import { CreateBookmarkDto } from './dto/create-bookmark.dto'

@Injectable()
export class BookmarkService {
  constructor(
    private readonly bookmarkRepository: BookmarkRepository,
    private readonly userRepository: UserRepository,
    private readonly groupRepository: GroupRepository,
  ) {}

  filterId = (arr: Array<{ id: string }>) => arr.map((data) => data.id)

  async getUpdatedBookmarksByTag(
    tag: string,
    bookmarkIds: Array<string>,
    updatedFrom: UpdatedFrom,
  ) {
    const updatedBookmarks = await this.bookmarkRepository.getUpdatedBookmarks(
      tag,
      updatedFrom,
    )
    const updatedBookmarkIds = this.filterId(updatedBookmarks)
    const addedBookmarks = await this.bookmarkRepository.getAddedBookmarks(
      bookmarkIds,
      updatedBookmarkIds,
    )

    await this.bookmarkRepository.updateBookmarkState(updatedBookmarkIds)

    const allBookmarkIds = await this.bookmarkRepository.getBookmarkIdsByTag(
      tag,
    )
    const deletedIds = bookmarkIds.filter((id) => !allBookmarkIds.includes(id))

    // TODO: responseDTO로 deletedIds 붙이기
    return updatedBookmarks.concat(addedBookmarks)
  }

  async createBookmark(createBookmarkDto: CreateBookmarkDto, source: Source) {
    const { url, title, summary, tags, group } = createBookmarkDto
    const username = 'root'

    // group 존재하는지 체크, 없으면에러
    if (!(await this.groupRepository.isGroupExist(username, group))) {
      throw new UnprocessableEntityException('Group does not exist')
    }

    const userTags = await this.userRepository.getTags(username)
    // TODO: Refactor, Transaction
    const userTagNames = userTags.map((tag) => tag.name)
    const newTags = []
    const existingTags = []
    tags.forEach((tag) =>
      userTagNames.includes(tag) ? existingTags.push(tag) : newTags.push(tag),
    )
    // TODO: User의 Tag 업데이트(있는건 ref + 1, 없는건 새로추가)
    await this.userRepository.createTags(username, newTags)
    await this.userRepository.incrementTagRef(username, existingTags)

    const tagObjects = tags.map((tag) => {
      return { name: tag, refCount: 1 }
    })
    const bookmark = await this.bookmarkRepository.create({
      data: {
        user: { connect: { username: username } },
        url: url,
        title: title,
        summary: summary,
        updated: source,
        tags: tagObjects,
        group: {
          connect: {
            username_name: {
              username: username,
              name: group,
            },
          },
        },
      },
    })

    //TODO: Responsedto 정리
    bookmark.tags = bookmark.tags.map((tag) => tag.name) as any
    return bookmark
  }
}
