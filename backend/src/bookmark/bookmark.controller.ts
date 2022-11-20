import {
  Body,
  Controller,
  Delete,
  Get,
  HttpException,
  InternalServerErrorException,
  Param,
  Post,
  Put,
  Query,
} from '@nestjs/common'
import { BookmarkService } from './bookmark.service'
import { CreateBookmarkDto } from './dto/create-bookmark.dto'
import { UpdateBookmarkDto } from './dto/update-bookmark.dto'

@Controller('bookmark')
export class BookmarkController {
  constructor(private readonly bookmarkService: BookmarkService) {}

  // TODO: Validator 적용
  @Get('tag')
  async getBookmarksByTag(@Query('name') tag: string) {
    try {
      return this.bookmarkService.getBookmarksByTag(tag)
    } catch (error) {
      // TODO: interceptor로 분리
      if (error instanceof HttpException) {
        throw error
      } else {
        throw new InternalServerErrorException()
      }
    }
  }

  @Get('tags')
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

  @Get('group')
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

  @Get(':id')
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

  @Post()
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

  @Put(':id')
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

  @Delete(':id')
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
