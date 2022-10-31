import { Module } from '@nestjs/common'
import { AppController } from './app.controller'
import { AppService } from './app.service'
import { NlpModule } from './nlp/nlp.module'

@Module({
  imports: [NlpModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
