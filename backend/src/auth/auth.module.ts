import { Module } from '@nestjs/common'
import { Hasher } from 'src/common/hasher'
import { UserModule } from 'src/user/user.module'
import { AuthController } from './auth.controller'
import { AuthService } from './auth.service'

@Module({
  imports: [UserModule],
  controllers: [AuthController],
  providers: [AuthService, Hasher],
})
export class AuthModule {}
