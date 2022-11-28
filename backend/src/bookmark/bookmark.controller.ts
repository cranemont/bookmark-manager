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
} from '@nestjs/common'
import { BookmarkService } from './bookmark.service'
import { CreateBookmarkDto } from './dto/create-bookmark.dto'
import { UpdateBookmarkDto } from './dto/update-bookmark.dto'

@Controller()
export class BookmarkController {
  constructor(private readonly bookmarkService: BookmarkService) {}

  @Get('bookmarks/search')
  async fullTextSearch(@Query('query') query: string) {
    try {
      return this.bookmarkService.fullTextSearch(query)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  // TODO: Validator 적용
  @Get('bookmarks/tag')
  async getBookmarksByTag(
    @Query('names', new ParseArrayPipe({ items: String, separator: ',' }))
    tags: Array<string>,
  ) {
    try {
      return this.bookmarkService.getBookmarksByTags(tags)
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
  async getTags() {
    try {
      return this.bookmarkService.getTags()
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Get('bookmarks/group')
  async getBookmarksByGroup(@Query('name') group: string) {
    try {
      return this.bookmarkService.getBookmarksByGroup(group)
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
  async getBookmarkById(@Param('id') id: string) {
    try {
      return this.bookmarkService.getBookmarkById(id)
    } catch (error) {
      // TODO: interceptor로 분리
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Post('bookmark')
  async createBookmark(@Body() createBookmarkDto: CreateBookmarkDto) {
    try {
      return this.bookmarkService.createBookmark(createBookmarkDto)
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
    @Param('id') id: string,
    @Body() updateBookmarkDto: UpdateBookmarkDto,
  ) {
    try {
      return this.bookmarkService.updateBookmark(id, updateBookmarkDto)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Delete('bookmark/:id')
  async deleteBookmark(@Param('id') id: string) {
    try {
      return this.bookmarkService.deleteBookmark(id)
    } catch (error) {
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }
}