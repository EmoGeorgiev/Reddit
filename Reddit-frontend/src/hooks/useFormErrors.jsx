import { useState } from 'react'

export const useFormErrors = () => {
    const [errors, setErrors] = useState({})

    const setBackendErrors = (errorResponse) => {
        const data = errorResponse?.response?.data
        const status = errorResponse?.response?.status
        
        if (data && typeof data === 'object') {
            setErrors(data)
        } else if (status === 401) {
            setErrors({ message: 'Invalid username or password' })
        }

        setTimeout(() => {
            clearErrors()
        }, 3000)
    }

    const clearErrors = () => {
        setErrors({})
    }

    return {
        errors,
        setBackendErrors,
        clearErrors
    }
}