import { Module } from '@nestjs/common'
import { NlpService } from './nlp.service'
import { NlpController } from './nlp.controller'
import { OneAIModule } from 'src/oneai/oneai.module'

@Module({
  imports: [OneAIModule],
  providers: [NlpService],
  controllers: [NlpController],
})
export class NlpModule {}
