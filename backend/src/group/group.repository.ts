import { Injectable } from '@nestjs/common'
import { PrismaService } from 'src/prisma/prisma.service'

@Injectable()
export class GroupRepository {
  constructor(private readonly prisma: PrismaService) {}

  async getGroupByName(userName: string, groupName: string) {
    return await this.prisma.group.findUnique({
      where: {
        username_name: {
          username: userName,
          name: groupName,
        },
      },
    })
  }

  async isGroupExist(userName: string, groupName: string) {
    const group = await this.getGroupByName(userName, groupName)
    return !!group
  }
}
