import { Injectable, PipeTransform } from '@nestjs/common'

@Injectable()
export class UrlValidationPipe implements PipeTransform {
  transform(value: string) {
    return new URL(value).toString()
  }
}
