import { Transform } from 'class-transformer'

export class BookmarkResponseDto {
  id: string
  url: string
  title: string
  summary: string

  @Transform(({ value }) => value.map((tag: { name: string }) => tag.name), {})
  tags: Array<string>

  @Transform(({ value }) => value.name, {})
  group: string
}

export const bookmarkReseponseFields = {
  id: true,
  url: true,
  title: true,
  summary: true,
  tags: {
    select: {
      name: true,
    },
  },
  group: {
    select: {
      name: true,
    },
  },
}
