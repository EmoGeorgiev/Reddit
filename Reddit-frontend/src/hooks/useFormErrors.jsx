import { useState } from 'react'

export const useFormErrors = () => {
    const [errors, setErrors] = useState({})

    const setBackendErrors = (errorResponse) => {
        const data = errorResponse?.response?.data;
        
        if (data && typeof data === 'object') {
            setErrors(data)

            setTimeout(() => {
                clearErrors()
            }, 3000)
        }
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