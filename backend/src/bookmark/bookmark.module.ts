import { Module } from '@nestjs/common'
import { PrismaService } from 'src/prisma/prisma.service'
import { BookmarkWebController } from './bookmark-web.controller'
import { BookmarkMobileController } from './bookmark-mobile.controller'
import { BookmarkService } from './bookmark.service'

@Module({
  controllers: [BookmarkWebController, BookmarkMobileController],
  providers: [BookmarkService, PrismaService],
})
export class BookmarkModule {}
