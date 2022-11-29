export class AuthenticatedUser {
  id!: string
  username!: string

  constructor(user: AuthenticatedUser) {
    Object.assign(this, user)
  }
}
