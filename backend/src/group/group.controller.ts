import {
  Body,
  Controller,
  Get,
  HttpException,
  InternalServerErrorException,
  Post,
  UseGuards,
} from '@nestjs/common'
import { AuthenticatedUser } from 'src/auth/authenticatedUser'
import { SessionAuthGuard } from 'src/auth/guards/session.guard'
import { User } from 'src/common/decorators/user.decorator'
import { CreateGroupDto } from './dto/create-group.dto'
import { GroupService } from './group.service'

@UseGuards(SessionAuthGuard)
@Controller()
export class GroupController {
  constructor(private readonly groupService: GroupService) {}

  @Get('groups')
  async getGroups(@User() user: AuthenticatedUser) {
    try {
      return this.groupService.getGroups(user.id)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Post('group')
  async createGroup(
    @User() user: AuthenticatedUser,
    @Body() createGroupDto: CreateGroupDto,
  ) {
    try {
      return this.groupService.createGroup(createGroupDto, user.id)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }
}
