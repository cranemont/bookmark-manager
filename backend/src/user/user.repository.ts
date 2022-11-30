import { Injectable } from '@nestjs/common'
import { PrismaService } from 'src/prisma/prisma.service'

@Injectable()
export class UserRepository {
  constructor(private readonly prisma: PrismaService) {}

  async getCredentialByUsername(username: string) {
    return await this.prisma.user.findUnique({
      where: {
        username: username,
      },
    })
  }

  async getById(id: string) {
    return await this.prisma.user.findUnique({
      where: {
        id: id,
      },
      select: {
        id: true,
        username: true,
        password: false,
      },
    })
  }

  async count(username: string) {
    return await this.prisma.user.count({
      where: {
        username: username,
      },
    })
  }

  async create(username: string, hashedPassword: string) {
    return await this.prisma.user.create({
      data: {
        username: username,
        password: hashedPassword,
      },
      select: {
        id: true,
        username: true,
        password: false,
      },
    })
  }
}
