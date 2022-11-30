import {
  Body,
  Controller,
  InternalServerErrorException,
  Logger,
  Post,
} from '@nestjs/common'
import { UrlValidationPipe } from 'src/common/pipes/url-validation.pipe'
import { NlpService } from './nlp.service'

@Controller('nlp')
export class NlpController {
  constructor(private readonly nplService: NlpService) {}

  // TODO: 캐시 붙이기
  @Post('summarize')
  async summarizeUrl(@Body('url', UrlValidationPipe) url: string) {
    try {
      return await this.nplService.summarizeUrl(url)
    } catch (err) {
      Logger.error(err)
      throw new InternalServerErrorException()
    }
  }
}
