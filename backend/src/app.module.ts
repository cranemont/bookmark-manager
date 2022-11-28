import { Module } from '@nestjs/common'
import { ConfigModule } from '@nestjs/config'
import { AppController } from './app.controller'
import { AppService } from './app.service'
import { NlpModule } from './nlp/nlp.module'
import { OneAIModule } from './oneai/oneai.module'
import { PrismaService } from './prisma/prisma.service'
import { BookmarkModule } from './bookmark/bookmark.module'
import { UserModule } from './user/user.module'
import { GroupModule } from './group/group.module'
import { AuthModule } from './auth/auth.module'

@Module({
  imports: [
    NlpModule,
    OneAIModule,
    ConfigModule.forRoot({ isGlobal: true }),
    BookmarkModule,
    UserModule,
    GroupModule,
    AuthModule,
  ],
  controllers: [AppController],
  providers: [AppService, PrismaService],
})
export class AppModule {}
