import { Module } from '@nestjs/common'
import { ConfigService } from '@nestjs/config'

import { OneAIService } from './oneai.service'
import OneAI from 'oneai'

const OneAIProvider = {
  provide: 'OneAI',
  useFactory: (config: ConfigService) => {
    return new OneAI(config.get('ONE_AI_API_KEY'))
  },
  inject: [ConfigService],
}

@Module({
  providers: [OneAIProvider, OneAIService],
  exports: [OneAIService],
})
export class OneAIModule {}
