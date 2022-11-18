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
        'Web',
      )
    } catch (error) {
      throw new InternalServerErrorException()
    }
  }
}
