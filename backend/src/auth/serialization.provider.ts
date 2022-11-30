import { Injectable } from '@nestjs/common'
import { PassportSerializer } from '@nestjs/passport'
import { User } from '@prisma/client'
import { UserService } from 'src/user/user.service'
import { AuthenticatedUser } from './authenticatedUser'

// source: https://dev.to/nestjs/setting-up-sessions-with-nestjs-passport-and-redis-210
@Injectable()
export class AuthSerializer extends PassportSerializer {
  constructor(private readonly userService: UserService) {
    super()
  }
  serializeUser(
    user: AuthenticatedUser,
    done: (err: Error, user: { id: string }) => void,
  ) {
    done(null, { id: user.id })
  }

  async deserializeUser(
    payload: { id: string },
    done: (err: Error, user: Omit<User, 'password'>) => void,
  ) {
    const user = await this.userService.getById(payload.id)
    done(null, user)
  }
}
