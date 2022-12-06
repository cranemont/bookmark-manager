import {
  Modal,
  ModalContent,
  ModalOverlay,
  ModalProps,
  Spinner,
  Text,
} from "@chakra-ui/react";

interface Props extends Omit<ModalProps, "children"> {
  isLoading: boolean;
  message: string;
}

export const PopupModal = ({
  isOpen,
  onClose,
  isLoading,
  message,
  ...props
}: Props) => {
  return (
    <Modal
      closeOnOverlayClick={false}
      isOpen={isOpen}
      onClose={onClose}
      size="xs"
      isCentered
      {...props}
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
