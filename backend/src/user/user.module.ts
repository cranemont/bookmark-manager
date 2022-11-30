import { Module } from '@nestjs/common'
import { UserService } from './user.service'
import { UserController } from './user.controller'
import { PrismaService } from 'src/prisma/prisma.service'
import { UserRepository } from './user.repository'

@Module({
  providers: [UserService, PrismaService, UserRepository],
  controllers: [UserController],
  exports: [UserService],
})
export class UserModule {}
