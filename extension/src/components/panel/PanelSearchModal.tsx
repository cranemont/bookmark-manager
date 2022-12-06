import {
  Button,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  ModalProps,
} from "@chakra-ui/react";

type Props = Omit<ModalProps, "children">;

export const PanelSearchModal = ({ isOpen, onClose, ...props }: Props) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose} {...props}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Search for bookmarks</ModalHeader>
        <ModalCloseButton />
        <ModalBody pb={6}>
          <Input placeholder="Input keyword here." />
        </ModalBody>
        <ModalFooter>
          <Button colorScheme="blue" mr={3}>
            Search
          </Button>
          <Button onClick={onClose}>Cancel</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};
