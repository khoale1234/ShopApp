import{
    IsString,
    IsNotEmpty,
    IsPhoneNumber,
    IsDate
} from 'class-validator';


export class RegisterDTO{
    @IsPhoneNumber()
  phone_number:string;

  @IsNotEmpty()
  @IsString()
  password:string;

  @IsNotEmpty()
  @IsString()
  retype_password:string;

  @IsString()
  fullname:string;
  
  @IsNotEmpty()
  @IsString()
  address:string

  facebook_account_id:number=0;
  google_account_id:number=0;

  @IsDate()
  date_of_birth:Date;

  role_id: number=1;
  constructor(data :any){
    this.fullname=data.fullName;
    this.phone_number=data.phone;
    this.password=data.password;
    this.retype_password=data.retypedPassword;
    this.address=data.address;  
    this.facebook_account_id=data.facebook_account_id||0;
    this.google_account_id=data.this.google_account_id||0;
    this.date_of_birth=data.date_of_birth;
    this.role_id=data.role_id||1;
  }
}