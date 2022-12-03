import {
  Body,
  Controller,
  Delete,
  Get,
  HttpException,
  InternalServerErrorException,
  Param,
  ParseArrayPipe,
  Post,
  Put,
  Query,
  UseGuards,
} from '@nestjs/common'
import { AuthenticatedUser } from 'src/auth/authenticatedUser'
import { SessionAuthGuard } from 'src/auth/guards/session.guard'
import { User } from 'src/common/decorators/user.decorator'
import { BookmarkService } from './bookmark.service'
import { CreateBookmarkDto } from './dto/create-bookmark.dto'
import { UpdateBookmarkDto } from './dto/update-bookmark.dto'

@UseGuards(SessionAuthGuard)
@Controller()
export class BookmarkController {
  constructor(private readonly bookmarkService: BookmarkService) {}

  @Get('bookmarks/search')
  async fullTextSearch(
    @User() user: AuthenticatedUser,
    @Query('query') query: string,
  ) {
    try {
      return this.bookmarkService.fullTextSearch(query, user.username)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Get('bookmarks/tag')
  async getBookmarksByTag(
    @User() user: AuthenticatedUser,
    @Query('names', new ParseArrayPipe({ items: String, separator: ',' }))
    tags: Array<string>,
  ) {
    try {
      return this.bookmarkService.getBookmarksByTags(tags, user.id)
    } catch (error) {
      // TODO: interceptor로 분리
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Get('bookmark/tags')
  async getTags(@User() user: AuthenticatedUser) {
    try {
      return this.bookmarkService.getTags(user.id)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Get('bookmarks/group')
  async getBookmarksByGroup(
    @User() user: AuthenticatedUser,
    @Query('name') group: string,
  ) {
    try {
      return this.bookmarkService.getBookmarksByGroup(group, user.id)
    } catch (error) {
      // TODO: interceptor로 분리
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Get('bookmark/:id')
  async getBookmarkById(
    @User() user: AuthenticatedUser,
    @Param('id') id: string,
  ) {
    try {
      return this.bookmarkService.getBookmarkById(id, user.id)
    } catch (error) {
      // TODO: interceptor로 분리
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Get('bookmarks')
  async getAllBookmarks(@User() user: AuthenticatedUser) {
    try {
      return this.bookmarkService.getBookmarks(user.id)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Post('bookmark')
  async createBookmark(
    @User() user: AuthenticatedUser,
    @Body() createBookmarkDto: CreateBookmarkDto,
  ) {
    try {
      return this.bookmarkService.createBookmark(createBookmarkDto, user.id)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Put('bookmark/:id')
  async updateBookmark(
    @User() user: AuthenticatedUser,
    @Param('id') id: string,
    @Body() updateBookmarkDto: UpdateBookmarkDto,
  ) {
    try {
      return this.bookmarkService.updateBookmark(id, updateBookmarkDto, user.id)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Delete('bookmark/:id')
  async deleteBookmark(
    @User() user: AuthenticatedUser,
    @Param('id') id: string,
  ) {
    try {
      return this.bookmarkService.deleteBookmark(id, user.id)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }
}
