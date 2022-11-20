import { Module } from '@nestjs/common'
import { PrismaService } from 'src/prisma/prisma.service'
import { BookmarkController } from './bookmark.controller'
import { BookmarkService } from './bookmark.service'
import { BookmarkRepository } from './bookmark.repository'
import { UserModule } from 'src/user/user.module'
import { GroupModule } from 'src/group/group.module'

@Module({
  imports: [UserModule],
  controllers: [BookmarkController],
  providers: [BookmarkService, PrismaService, BookmarkRepository],
})
export class BookmarkModule {}
