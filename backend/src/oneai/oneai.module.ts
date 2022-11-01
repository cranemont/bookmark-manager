import { Module } from '@nestjs/common'
import OneAI from 'oneai'
import { OneAIService } from './oneai.service'

@Module({
  providers: [OneAIService],
  exports: [OneAIService],
})
export class OneAIModule {}
