import { Injectable } from '@nestjs/common'
import { userId } from 'src/common/constants'
import { CreateGroupDto } from './dto/create-group.dto'
import { GroupRepository } from './group.repository'

@Injectable()
export class GroupService {
  constructor(private readonly groupRespository: GroupRepository) {}

  async getGroups() {
    const groups = await this.groupRespository.getGroups(userId)
    return Array.from(
      new Set(
        groups.reduce((names, group) => {
          names.push(group.name)
          return names
        }, []),
      ),
    )
  }

  async createGroup(createGroupDto: CreateGroupDto) {
    return await this.groupRespository.create({
      data: {
        userId: userId,
        name: createGroupDto.name,
      },
    })
  }
}
