import { Injectable } from '@nestjs/common'
import { PrismaService } from 'src/prisma/prisma.service'

@Injectable()
export class UserRepository {
  constructor(private readonly prisma: PrismaService) {}

  async getUser(username: string) {
    return await this.prisma.user.findUnique({
      where: {
        username: username,
      },
    })
  }
}
