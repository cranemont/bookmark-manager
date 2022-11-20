import {
  Body,
  Controller,
  Get,
  HttpException,
  InternalServerErrorException,
  Post,
  Query,
} from '@nestjs/common'
import { BookmarkService } from './bookmark.service'
import { CreateBookmarkDto } from './dto/create-bookmark.dto'

@Controller('web/bookmark')
export class BookmarkWebController {
  constructor(private readonly bookmarkService: BookmarkService) {}

  // TODO: Validator 적용
  @Get()
  async getBookmarksByTag(
    @Query('tag') tag: string,
    @Body('bookmarkIds') bookmarkIds: Array<string>,
  ) {
    try {
      return this.bookmarkService.getUpdatedBookmarksByTag(
        tag,
        bookmarkIds,
        'Mobile',
      )
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
      return this.bookmarkService.createBookmark(createBookmarkDto, 'Web')
    } catch (error) {
      throw new InternalServerErrorException()
    }
  }
}
