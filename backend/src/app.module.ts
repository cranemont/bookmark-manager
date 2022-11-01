import { Module } from '@nestjs/common'
import { ConfigModule } from '@nestjs/config'
import { AppController } from './app.controller'
import { AppService } from './app.service'
import { NlpModule } from './nlp/nlp.module'
import { OneAIModule } from './oneai/oneai.module'

@Module({
  imports: [NlpModule, OneAIModule, ConfigModule.forRoot({ isGlobal: true })],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
