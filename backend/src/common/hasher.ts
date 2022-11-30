import { Injectable } from '@nestjs/common'
import bcrypt from 'bcrypt'

@Injectable()
export class Hasher {
  async hash(plainText: string) {
    return await bcrypt.hash(plainText, 5)
  }

  async compare(plainText: string, hashedPassword: string) {
    return await bcrypt.compare(plainText, hashedPassword)
  }
}
