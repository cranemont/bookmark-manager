import { Injectable } from '@nestjs/common'
import { Prisma } from '@prisma/client'
import { PrismaService } from 'src/prisma/prisma.service'

const groupResponseFields = {}

@Injectable()
export class GroupRepository {
  constructor(private readonly prisma: PrismaService) {}

  async getGroups(userId: string) {
    return await this.prisma.group.findMany({
      where: {
        userId: userId,
      },
      select: {
        name: true,
      },
    })
  }

  async getGroupByName(userId: string, groupName: string) {
    return await this.prisma.group.findUnique({
      where: {
        name_userId: {
          userId: userId,
          name: groupName,
        },
      },
    })
  }

  async isGroupExist(userName: string, groupName: string) {
    const group = await this.getGroupByName(userName, groupName)
    return !!group
  }

  async create(data: Prisma.GroupCreateArgs) {
    return await this.prisma.group.create({
      ...data,
    })
  }
}
