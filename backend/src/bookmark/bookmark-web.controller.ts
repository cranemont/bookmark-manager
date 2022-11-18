import {
  Body,
  Controller,
  Get,
  InternalServerErrorException,
  Query,
} from '@nestjs/common'
import { BookmarkService } from './bookmark.service'

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
      throw new InternalServerErrorException()
    }
  }
}
