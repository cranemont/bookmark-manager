import {
  Modal,
  ModalContent,
  ModalOverlay,
  Spinner,
  Text,
} from "@chakra-ui/react";

export const PopupModal = ({
  isOpen,
  onClose,
  isLoading,
  message,
  ...props
}) => {
  return (
    <Modal
      closeOnOverlayClick={false}
      isOpen={isOpen}
      onClose={onClose}
      size="xs"
      isCentered
    >
      <ModalOverlay />
      <ModalContent width="fit-content" padding={4}>
        {isLoading ? (
          <Spinner size="xl" />
        ) : (
          <Text fontSize="md">{message}</Text>
        )}
      </ModalContent>
    </Modal>
  );
};
