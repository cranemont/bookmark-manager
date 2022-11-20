import { Module } from '@nestjs/common'
import { PrismaService } from 'src/prisma/prisma.service'
import { GroupController } from './group.controller'
import { GroupRepository } from './group.repository'
import { GroupService } from './group.service'

@Module({
  controllers: [GroupController],
  providers: [GroupService, PrismaService, GroupRepository],
  exports: [GroupRepository],
})
export class GroupModule {}
