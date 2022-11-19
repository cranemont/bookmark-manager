import { Module } from '@nestjs/common'
import { ConfigModule } from '@nestjs/config'
import { AppController } from './app.controller'
import { AppService } from './app.service'
import { NlpModule } from './nlp/nlp.module'
import { OneAIModule } from './oneai/oneai.module'
import { PrismaService } from './prisma/prisma.service'
import { BookmarkModule } from './bookmark/bookmark.module'
import { UserModule } from './user/user.module'

@Module({
  imports: [
    NlpModule,
    OneAIModule,
    ConfigModule.forRoot({ isGlobal: true }),
    BookmarkModule,
    UserModule,
  ],
  controllers: [AppController],
  providers: [AppService, PrismaService],
})
export class AppModule {}
