import { Injectable } from '@nestjs/common'
import { Prisma } from '@prisma/client'
import { PrismaService } from 'src/prisma/prisma.service'
import { bookmarkReseponseFields } from './dto/bookmark.response.dto'

@Injectable()
export class BookmarkRepository {
  constructor(private readonly prisma: PrismaService) {}

  async isExist(id: string) {
    return await this.prisma.bookmark.count({
      where: {
        id: id,
      },
    })
  }

  async fullTextSearch(query: string) {
    return await this.prisma.bookmark.aggregateRaw({
      pipeline: [
        {
          $search: {
            index: 'bookmark-search',
            text: {
              query: query,
              path: {
                wildcard: '*',
              },
            },
          },
        },
        {
          $lookup: {
            from: 'Group',
            localField: 'groupId',
            foreignField: '_id',
            as: 'group',
          },
        },
        {
          $project: {
            id: {
              $toString: '$_id',
            },
            url: 1,
            title: 1,
            summary: 1,
            tags: 1,
            'group.name': 1,
          },
        },
      ],
    })
  }

  async getBookmarkById(id: string, userId: string) {
    return await this.prisma.bookmark.findUnique({
      where: {
        id_userId: {
          id,
          userId,
        },
      },
      select: bookmarkReseponseFields,
    })
  }

  async getBookmarksByTags(tag: Array<string>, userId: string) {
    return await this.prisma.bookmark.findMany({
      where: { user: { id: userId }, tags: { some: { name: { in: tag } } } },
      select: bookmarkReseponseFields,
    })
  }

  async getBookmarksByGroup(group: string, userId: string) {
    return await this.prisma.bookmark.findMany({
      where: {
        user: { id: userId },
        group: { name: group },
      },
      select: bookmarkReseponseFields,
    })
  }

  async getTags(userId: string) {
    return await this.prisma.bookmark.findMany({
      where: { userId: userId },
      select: { tags: true },
    })
  }

  async create(data: Prisma.BookmarkCreateArgs) {
    return await this.prisma.bookmark.create({
      ...data,
      select: bookmarkReseponseFields,
    })
  }

  async update(data: Prisma.BookmarkUpdateArgs) {
    return await this.prisma.bookmark.update({
      ...data,
      select: bookmarkReseponseFields,
    })
  }

  async delete(id: string, userId: string) {
    return await this.prisma.bookmark.delete({
      where: {
        id_userId: {
          id: id,
          userId: userId,
        },
      },
    })
  }
}
