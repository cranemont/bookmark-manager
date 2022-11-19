import { Injectable } from '@nestjs/common'
import { UpdatedFrom } from '@prisma/client'
import { BookmarkRepository } from './bookmark.repository'
import { CreateBookmarkDto } from './dto/create-bookmark.dto'

@Injectable()
export class BookmarkService {
  constructor(private readonly bookmarkRepository: BookmarkRepository) {}

  async getUpdatedBookmarksByTag(
    tag: string,
    bookmarkIds: Array<string>,
    updatedFrom: UpdatedFrom,
  ) {
    const updatedBookmarks = await this.bookmarkRepository.getUpdatedBookmarks(
      tag,
      updatedFrom,
    )
    const updatedBookmarkIds = this.filterId(updatedBookmarks)
    const addedBookmarks = await this.bookmarkRepository.getAddedBookmarks(
      bookmarkIds,
      updatedBookmarkIds,
    )

    await this.bookmarkRepository.updateBookmarkState(updatedBookmarkIds)
    return updatedBookmarks.concat(addedBookmarks)
  }

  filterId = (arr: Array<{ id: string }>) => arr.map((data) => data.id)
}
