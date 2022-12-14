import { User } from '@prisma/client'

export class AuthenticatedUser {
  id!: string
  username!: string

  constructor(user: User) {
    this.id = user.id
    this.username = user.username
  }
}
