import {
  Body,
  Controller,
  Get,
  HttpException,
  InternalServerErrorException,
  Post,
} from '@nestjs/common'
import { CreateGroupDto } from './dto/create-group.dto'
import { GroupService } from './group.service'

@Controller()
export class GroupController {
  constructor(private readonly groupService: GroupService) {}

  @Get('groups')
  async getGroups() {
    try {
      return this.groupService.getGroups()
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Post('group')
  async createGroup(@Body() createGroupDto: CreateGroupDto) {
    try {
      return this.groupService.createGroup(createGroupDto)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }
}
