import { UpdatedFrom } from '@prisma/client'
import { PrismaService } from 'src/prisma/prisma.service'

export class BookmarkRepository {
  constructor(private readonly prisma: PrismaService) {}

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
