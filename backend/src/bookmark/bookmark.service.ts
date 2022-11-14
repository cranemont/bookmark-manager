import { Injectable } from '@nestjs/common'
import { PrismaService } from 'src/prisma/prisma.service'

@Injectable()
export class BookmarkService {
  constructor(private readonly prisma: PrismaService) {}

  async getMobileBookmarksByTag(tag: string, bookmarkIds: Array<string>) {
    const updatedBookmarks = await this.prisma.bookmark.findMany({
      where: { updated: 'Web' },
    })

    const updatedBookmarkIds = updatedBookmarks.map((bookmark) => bookmark.id)
    const addedBookmarks = await this.prisma.bookmark.findMany({
      where: {
        id: {
          notIn: [...new Set(bookmarkIds.concat(updatedBookmarkIds))],
        },
      },
    })

    await this.prisma.bookmark.updateMany({
      where: {
        id: {
          in: updatedBookmarkIds,
        },
      },
      data: {
        updated: 'None',
      },
    })

    return updatedBookmarks.concat(addedBookmarks)
  }
}
