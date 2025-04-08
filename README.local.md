# Local Development Security Considerations

## Environment Variables and Credentials

### Importance

This project uses environment variables to manage sensitive configuration like database credentials, API keys, and other secrets.

### Security Best Practices

1. **NEVER commit .env files to version control**

   - The `.env` file is listed in `.gitignore` to prevent accidental commits
   - Always use `.env.example` as a template with placeholder values

2. **Creating Your Local Environment**

   - Copy `.env.example` to `.env`
   - Fill in your local development credentials
   - Keep this file private to your development machine

3. **Managing Secrets in Production**
   - In production environments, set environment variables through your hosting platform
   - Railway.app provides environment variable management in their dashboard

## Temporary Schema Validation Workaround

A schema validation issue currently exists between entity models and the database:

- The `birthdate` column in the `users` table is defined as `date` in the database
- The entity model expects it to be `timestamp(6)`

### Current Workaround

Schema validation has been temporarily disabled in `application.properties`:

```properties
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false
```

### Proper Resolution (To Be Implemented)

This should be resolved by either:

1. Updating the entity models to match the database schema, or
2. Migrating the database schema to match entity models

## Security Headers and CORS

Make sure to test security headers and CORS configuration before deploying to production.

## JWT Secret Key Security

The JWT secret key should be:

- At least 32 characters long
- Randomly generated
- Unique per environment
- Never shared outside of secure channels
