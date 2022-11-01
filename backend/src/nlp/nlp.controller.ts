import {
  Body,
  Controller,
  Get,
  InternalServerErrorException,
  Logger,
  Param,
  Post,
} from '@nestjs/common'
import { NlpService } from './nlp.service'

@Controller('nlp')
export class NlpController {
  constructor(private readonly nplService: NlpService) {}

  // TODO: 캐시 붙이기
  @Post('summarize')
  async summarizeByUrl(@Body('url') url: string) {
    try {
      return await this.nplService.summarizeByUrl(url)
    } catch (err) {
      Logger.error(err)
      return new InternalServerErrorException()
    }
  }
}
