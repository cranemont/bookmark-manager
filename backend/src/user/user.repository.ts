import { Injectable } from '@nestjs/common'
import { Prisma } from '@prisma/client'
import { PrismaService } from 'src/prisma/prisma.service'

class Tag {
  name: string
  refCount: number
}

@Injectable()
export class UserRepository {
  constructor(private readonly prisma: PrismaService) {}

  async getTags(username: string) {
    const user = await this.prisma.user.findUnique({
      where: { username: username },
      select: {
        tags: true,
      },
    })
    return user.tags
  }

  async createTags(username: string, tags: Array<string>) {
    //TODO: Refactor as class
    const tagObjects = tags.map((tag) => {
      return { name: tag, refCount: 1 }
    })
    return await this.prisma.user.update({
      where: {
        username: username,
      },
      data: {
        tags: {
          push: tagObjects,
        },
      },
    })
  }

  async incrementTagRef(username: string, tags: Array<string>) {
    return await this.prisma.user.update({
      where: {
        username: username,
      },
      data: {
        tags: {
          updateMany: {
            where: {
              name: {
                in: tags,
              },
            },
            data: {
              refCount: {
                increment: 1,
              },
            },
          },
        },
      },
    })
  }
}
