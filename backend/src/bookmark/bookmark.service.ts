import { Injectable, NotFoundException } from '@nestjs/common'
import { UserRepository } from 'src/user/user.repository'
import { BookmarkRepository } from './bookmark.repository'
import { CreateBookmarkDto } from './dto/create-bookmark.dto'
import { UpdateBookmarkDto } from './dto/update-bookmark.dto'
import { userId } from 'src/common/constants'

@Injectable()
export class BookmarkService {
  constructor(
    private readonly bookmarkRepository: BookmarkRepository,
    private readonly userRepository: UserRepository,
  ) {}

  filterId = (arr: Array<{ id: string }>) => arr.map((data) => data.id)

  async getBookmarkById(id: string) {
    const bookmark = await this.bookmarkRepository.getBookmarkById(id, userId)
    if (!bookmark) {
      throw new NotFoundException('Bookmark does not exist')
    }
    return bookmark
  }

  async getBookmarksByTag(tag: string) {
    return await this.bookmarkRepository.getBookmarksByTag(tag, userId)
  }

  async getTags() {
    const bookmarks = await this.bookmarkRepository.getTags(userId)
    return Array.from(
      new Set(
        bookmarks.reduce((tags, bookmark) => {
          return tags.concat(bookmark.tags.map((tag) => tag.name))
        }, []),
      ),
    )
  }

  async getBookmarksByGroup(group: string) {
    return await this.bookmarkRepository.getBookmarksByGroup(group, userId)
  }

  async createBookmark(createBookmarkDto: CreateBookmarkDto) {
    const { url, title, summary, tags, group } = createBookmarkDto
    const tagObjects = tags.map((tag) => {
      return { name: tag }
    })

    const bookmark = await this.bookmarkRepository.create({
      data: {
        user: { connect: { id: userId } },
        url: url,
        title: title,
        summary: summary,
        tags: tagObjects,
        group: {
          connectOrCreate: {
            where: {
              name_userId: {
                userId: userId,
                name: group,
              },
            },
            create: {
              userId: userId,
              name: group,
            },
          },
        },
      },
    })
    return bookmark
  }

  async updateBookmark(id: string, updateBookmarkDto: UpdateBookmarkDto) {
    const { url, title, summary, tags, group } = updateBookmarkDto

    if (!(await this.bookmarkRepository.isExist(id))) {
      throw new NotFoundException('Bookmark does not exist')
    }

    const tagObjects = tags.map((tag) => {
      return { name: tag }
    })

    return await this.bookmarkRepository.update({
      where: {
        id_userId: {
          id: id,
          userId: userId,
        },
      },
      data: {
        user: { connect: { id: userId } },
        url: url,
        title: title,
        summary: summary,
        tags: tagObjects,
        group: {
          connectOrCreate: {
            where: {
              name_userId: {
                userId: userId,
                name: group,
              },
            },
            create: {
              userId: userId,
              name: group,
            },
          },
        },
      },
    })
  }

  async deleteBookmark(id: string) {
    if (!(await this.bookmarkRepository.isExist(id))) {
      throw new NotFoundException('Bookmark does not exist')
    }
    return await this.bookmarkRepository.delete(id, userId)
  }
}
