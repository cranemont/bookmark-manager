import { Injectable } from '@nestjs/common'
import { Prisma } from '@prisma/client'
import { PrismaService } from 'src/prisma/prisma.service'

const bookmarkReseponseFields = {
  id: true,
  url: true,
  title: true,
  summary: true,
  tags: {
    select: {
      name: true,
    },
  },
  group: {
    select: {
      name: true,
    },
  },
}

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

  async getBookmarksByTag(tag: string, userId: string) {
    return await this.prisma.bookmark.findMany({
      where: { user: { id: userId }, tags: { some: { name: tag } } },
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
