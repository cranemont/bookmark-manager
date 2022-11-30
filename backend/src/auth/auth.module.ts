import { Module } from '@nestjs/common'
import { PassportModule } from '@nestjs/passport'
import { Hasher } from 'src/common/hasher'
import { UserModule } from 'src/user/user.module'
import { AuthController } from './auth.controller'
import { AuthService } from './auth.service'
import { AuthSerializer } from './serialization.provider'
import { LocalStrategy } from './strategy/local.strategy'

@Module({
  imports: [UserModule, PassportModule.register({ session: true })],
  controllers: [AuthController],
  providers: [AuthService, Hasher, LocalStrategy, AuthSerializer],
})
export class AuthModule {}
