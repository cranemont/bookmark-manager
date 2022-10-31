import { Module } from '@nestjs/common'
import { OneaiService } from './oneai.service'

@Module({
  providers: [OneaiService],
  exports: [OneaiModule],
})
export class OneaiModule {}
