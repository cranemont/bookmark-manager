import { Injectable } from '@nestjs/common'
import { Prisma } from '@prisma/client'
import { PrismaService } from 'src/prisma/prisma.service'

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

  async getGroupByName(groupName: string, userId: string) {
    return await this.prisma.group.findUnique({
      where: {
        name_userId: {
          userId: userId,
          name: groupName,
        },
      },
    })
  }

  async isGroupExist(groupName: string, userId: string) {
    const group = await this.getGroupByName(groupName, userId)
    return !!group
  }

  async create(data: Prisma.GroupCreateArgs) {
    return await this.prisma.group.create({
      ...data,
    })
  }
}
