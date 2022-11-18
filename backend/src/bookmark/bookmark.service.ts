import { Injectable } from '@nestjs/common'
import { UpdatedFrom } from '@prisma/client'
import { PrismaService } from 'src/prisma/prisma.service'

@Injectable()
export class BookmarkService {
  constructor(private readonly prisma: PrismaService) {}

  async getUpdatedBookmarksByTag(
    tag: string,
    bookmarkIds: Array<string>,
    updatedFrom: UpdatedFrom,
  ) {
    const updatedBookmarks = await this.getUpdatedBookmarks(tag, updatedFrom)
    const updatedBookmarkIds = this.filterId(updatedBookmarks)
    const addedBookmarks = await this.getAddedBookmarks(
      bookmarkIds,
      updatedBookmarkIds,
    )

    await this.updateBookmarkState(updatedBookmarkIds)
    return updatedBookmarks.concat(addedBookmarks)
  }

  filterId = (arr: Array<{ id: string }>) => arr.map((data) => data.id)

  async getUpdatedBookmarks(tag: string, from: UpdatedFrom) {
    return await this.prisma.bookmark.findMany({
      where: { tags: { some: { name: tag } }, AND: { updated: from } },
    })
  }

  async getAddedBookmarks(
    IdsFromUser: Array<string>,
    updatedIds: Array<string>,
  ) {
    return await this.prisma.bookmark.findMany({
      where: {
        id: {
          notIn: [...new Set(IdsFromUser.concat(updatedIds))],
        },
      },
    })
  }

  async updateBookmarkState(updatedIds: Array<string>) {
    await this.prisma.bookmark.updateMany({
      where: {
        id: {
          in: updatedIds,
        },
      },
      data: {
        updated: 'None',
      },
    })
  }
}
