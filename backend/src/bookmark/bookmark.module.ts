import { Module } from '@nestjs/common'
import { PrismaService } from 'src/prisma/prisma.service'
import { BookmarkWebController } from './bookmark-web.controller'
import { BookmarkMobileController } from './bookmark-mobile.controller'
import { BookmarkService } from './bookmark.service'
import { BookmarkRepository } from './bookmark.repository'
import { UserModule } from 'src/user/user.module'
import { GroupModule } from 'src/group/group.module'

@Module({
  imports: [UserModule, GroupModule],
  controllers: [BookmarkWebController, BookmarkMobileController],
  providers: [BookmarkService, PrismaService, BookmarkRepository],
})
export class BookmarkModule {}
