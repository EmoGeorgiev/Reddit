import closeIcon from '../../assets/close-icon.svg'

const FormHeader = ({ name, handleClose }) => {
    return (
        <div className='active-form-header'>
            <h1 className='active-form-heading'>{name}</h1>

            <button onClick={handleClose}>
                <img className='close-btn' src={closeIcon} alt='close' />
            </button>
        </div>
    )
}

export default FormHeader