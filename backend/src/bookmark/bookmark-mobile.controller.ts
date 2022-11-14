import {
  Body,
  Controller,
  Get,
  InternalServerErrorException,
  Query,
} from '@nestjs/common'
import { BookmarkService } from './bookmark.service'

@Controller('mobile/bookmark')
export class BookmarkMobileController {
  constructor(private readonly bookmarkService: BookmarkService) {}

  @Get()
  async getBookmarksByTag(
    @Query('tag') tag: string,
    @Body('bookmarkIds') bookmarkIds: Array<string>,
  ) {
    try {
      return this.bookmarkService.getMobileBookmarksByTag(tag, bookmarkIds)
    } catch (error) {
      throw new InternalServerErrorException()
    }
  }
}
