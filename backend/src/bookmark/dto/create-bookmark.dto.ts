import { Source, UpdatedFrom } from '@prisma/client'
import { IsIn, IsNotEmpty, IsString, IsUrl } from 'class-validator'

export class Tag {
  @IsString()
  @IsNotEmpty()
  name: string
}

export class Group {
  @IsString()
  @IsNotEmpty()
  name: string

  @IsIn([Source.Mobile, Source.Web])
  from: Source
}

export class CreateBookmarkDto {
  // user: Prisma.UserCreateNestedOneWithoutBookmarkInput;
  @IsUrl()
  @IsNotEmpty()
  url: string

  @IsString()
  @IsNotEmpty()
  title: string

  @IsString()
  @IsNotEmpty()
  summary: string

  @IsString({ each: true })
  @IsNotEmpty()
  tags: Array<string>

  @IsString()
  @IsNotEmpty()
  group: string
}
