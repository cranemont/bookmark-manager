import { IsNotEmpty, IsString, IsUrl } from 'class-validator'

export class UpdateBookmarkDto {
  @IsUrl()
  @IsNotEmpty()
  url: string

  @IsString()
  @IsNotEmpty()
  title: string

  @IsString()
  summary: string

  @IsString({ each: true })
  @IsNotEmpty()
  tags: Array<string>

  @IsString()
  @IsNotEmpty()
  group: string
}
